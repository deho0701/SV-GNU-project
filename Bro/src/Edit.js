import React from 'react';
import axios from 'axios';

class Edit extends React.Component{
    constructor(props){
        super(props);
        /****
        state 설명
        id: 현재 테이블 개수
        tables: 테이블 객체
        ****/
        this.state = {
            id: 1,
            tables: [],
            file: '',
            previewURL: 'http://117.16.164.14:5050/web/photo',
            diffX: 0,
            diffY: 0,
            shopX: 502,
            shopY: 350,
            dragging: false,
            data:''
        }

        this._dragStart = this._dragStart.bind(this);
        this._dragging = this._dragging.bind(this);
        this._dragEnd = this._dragEnd.bind(this);
    }

    callApi = async ()=>{
        await axios.post("http://117.16.164.14:5050/web/pre_seat", {name:'cafe 502'}
        ).then((res)=>{
            console.log(res)
            this.setState({
                id: res.data.length+1,
                tables: res.data
            })
        }
        );
    };

    componentDidMount(){
        this.callApi();
    }

    // 도면 삽입
    handleFileInput = (e) => {
        let reader = new FileReader();
        let file = e.target.files[0];
        console.log(file);
        reader.onloadend = () => {
            this.setState({
                file : file,
                previewURL : reader.result
            })
        }
        reader.readAsDataURL(file);
    }

    // 자리 추가
    addTable = () => {
        const { tables } = this.state;
        this.setState({
            tables: tables.concat({table_id:this.state.tables.length+1, table_x:0, table_y:0})
        })
        console.log('add');
    }

    // 자리 저장
    save = () => {
        
        // 서버에 데이터 저장
        const url = "http://117.16.164.14:5050/web/seat";
        const formData = new FormData();
        const config = {
            header: {'content-type': 'multipart/form-data'}
        }
        formData.append("img", this.state.file);
        axios.post("http://117.16.164.14:5050/web/photo_up", formData).then((res)=>console.log(res));
        
        var data = {};
        data.name = this.props.name;
        data.tables = this.state.tables;
        data.shopX = this.state.shopX;
        data.shopY = this.state.shopY;
        console.log(JSON.stringify(data));
        axios.post(url, data).then((res)=>console.log(res));
        //axios.post("http://117.16.164.14:5050/web/seat", {why:'andam'}).then((res) => console.log(res));
        console.log(this.state.file);
        console.log(this.state.tables);
        console.log('saved');
    }

    // 자리 삭제
    delete = id => {
        console.log(id);
        //const { tables } = this.state;
        let tb = this.state.tables.filter((table)=> table.table_id !== id);
        console.log(tb);
        this.setState({
            tables: tb.map(
                (table, index) => table.table_id === index+1 
                ? table
                : {table_id:index+1, table_x:table.table_x, table_y:table.table_y})
        })
        console.log(this.state.id);
    }

    // 자리 이동
    _dragStart(e) {
        console.log(e.target.id);
        console.log(this.state.dragging)
        console.log(e.clientX - e.currentTarget.getBoundingClientRect().left);
        this.setState({
            diffX: e.clientX - e.currentTarget.getBoundingClientRect().left,
            diffY: e.clientY - e.currentTarget.getBoundingClientRect().top,
            dragging: true
        });

        console.log(this.state.diffX);

    }

    _dragging(e) {
        if(this.state.dragging){
            var left = e.pageX - this.state.diffX - this.state.shopX;
            var top = e.pageY - this.state.diffY - this.state.shopY;

            this.setState({
                tables: this.state.tables.map(
                    table=> table.table_id === Number(e.target.id)
                    ? {table_id:table.table_id, table_x:left, table_y:top}
                    : table)
            });
        }
    }

    _dragEnd(e) {
        this.setState({
            dragging: false,
        });
        console.log(this.state.dragging)

    }

    render() {
        console.log(this.state.tables);
        return(
            <div id="container" className="container">
                <div className="title">
                    <p>자리 배치</p>
                </div>
                <div id="shop" className="shop">
                    <h1>Shop</h1>
                    <div className="btn_box">
                        <button id='add_btn' className='btn_style' onClick={this.addTable}>추가</button>
                        <input type="file" accept="image/jpg, image/png, image/jpeg, image/gif" className='imgFile' name='img' onChange={this.handleFileInput}></input>
                        <button id="save_btn" className='btn_style' onClick={this.save}>저장</button>
                    </div>
                    <div className="img_container">
                    <img className='floor_img' src={this.state.previewURL}/>
                        {this.state.tables.map(table=>{
                        return(
                            <div
                                className="table"
                                id={table.table_id}
                                style={{left:table.table_x, top:table.table_y}}
                                onMouseDown={this._dragStart}
                                onMouseMove={this._dragging}
                                onMouseUp={this._dragEnd}
                            >{table.table_id}<button id='delete_btn' className='btn_style' onClick={() => {this.delete(table.table_id);}}>x</button>
                            </div>
                        )})}
                    </div>
                </div>
            </div>
        )
    }
}

export default Edit;
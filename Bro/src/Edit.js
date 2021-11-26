import React from 'react';
import axios from 'axios';

class Edit extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id: 1,
            tables: [
                //{id:1, x:10, y:10},
                //{id:2, x:20, y:25},
                //{id:3, x:15, y:15}
            ],
            file: '',
            previewURL: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD…UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH//2Q==',
            diffX: 0,
            diffY: 0,
            shopX: 288,
            shopY: 176,
            dragging: false
        }

        this._dragStart = this._dragStart.bind(this);
        this._dragging = this._dragging.bind(this);
        this._dragEnd = this._dragEnd.bind(this);
    }

    // 도면 삽입
    handleFileInput = (e) => {
        let reader = new FileReader();
        let file = e.target.files[0];
        reader.onloadend = () => {
            this.setState({
                file : file,
                previewURL : reader.result
            })
        }
        reader.readAsDataURL(file);
    }

    componentWillMount(){
        const tables = localStorage.tables;
        if(tables){
            this.setState({
                previewURL: JSON.parse(localStorage.previewURL),
                tables: JSON.parse(tables),
                id: JSON.parse(localStorage.id)
            })          
        }
    }

    // 자리 추가
    addTable = () => {
        const { tables } = this.state;
        this.setState({
            tables: tables.concat({ id:this.state.id++, x:0, y:0})
        })
        console.log('add');
    }

    // 자리 저장
    save = () => {
        //로컬에저장
        localStorage.previewURL = JSON.stringify(this.state.previewURL);
        localStorage.id = JSON.stringify(this.state.id);
        localStorage.tables = JSON.stringify(this.state.tables);
        
        // 서버에 데이터 저장
        const url = "http://117.16.164.14:5050/web/pre_seat";
        const fd = new FormData();
        fd.append('shopimg', this.state.previewURL);
        fd.append('tables', this.state.tables);
        fd.append('shopX', JSON.stringify(this.state.shopX));
        fd.append('shopY', this.state.shopY);
        axios.post(url, fd, {
            headers: {
                'Content-Type': 'multipart/form-data'
            }
        }).then((res)=>console.log(res));
        //axios.post("http://117.16.164.14:5050/reserve", this.state.tables).then((res) => console.log(res));

        console.log(this.state.tables);
        console.log('saved');
    }

    // 자리 삭제
    delete = id => {
        console.log(id);
        const { tables } = this.state;
        this.setState({
            tables: tables.filter(table => table.id !== id),
            id: this.state.id-1
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
                    table=> table.id === Number(e.target.id)
                    ? {id:table.id, x:left, y:top}
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
        return(
            <div id="container" className="container">
                    <div className="title">
                        <p>자리 배치</p>
                    </div>
                    <button onClick={this.addTable}>추가</button>
                    <input type="file" name='imgFile' onChange={this.handleFileInput}></input>
                    <button id="save" onClick={this.save}>저장</button>
                    <div className="w">
                        
                        <div id="shop" className="shop">
                            <p>Shop</p>
                            <img className='floor_img' src={this.state.previewURL}/>
                            {this.state.tables.map(table=>{
                            return(
                                <div
                                    className="table"
                                    id={table.id}
                                    style={{left:table.x, top:table.y}}
                                    onMouseDown={this._dragStart}
                                    onMouseMove={this._dragging}
                                    onMouseUp={this._dragEnd}
                                >{table.id}<button className='deleteButton' onClick={() => {this.delete(table.id);}}>x</button></div>
                            )})}
                        </div>
                    </div>
                    
                    
                    <div className="status"></div>
                </div>
        )
    }
}

export default Edit;
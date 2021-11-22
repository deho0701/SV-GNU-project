import React from 'react';
import img from './sample1.JPG';
import Setting from './Setting';
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';
import axios from 'axios';

class Container extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            id: 1,
            setting:{name:'aa'},
            tables: [
                //{id:1, x:10, y:10},
                //{id:2, x:20, y:25},
                //{id:3, x:15, y:15}
            ],
            reservation: [
                {id:1, table:1, time:'13:00', name:'Yun'},
                {id:2, table:2, time:'15:00', name:'Gyu'},
                {id:3, table:1, time:'20:00', name:'Kim'}
            ],
            customer: [
                {cid:1, name:'Yun'}
            ],
            file: '',
            previewURL: 'data:image/jpeg;base64,/9j/4AAQSkZJRgABAQEAeAB4AAD…UUUUAFFFFABRRRQAUUUUAFFFFABRRRQAUUUUAFFFFAH//2Q==',
            profile: '',
            profileURL: '',
            diffX: 0,
            diffY: 0,
            shopX: 288,
            shopY: 176,
            dragging: false,
            isOpenPopup : false
        }

        this._dragStart = this._dragStart.bind(this);
        this._dragging = this._dragging.bind(this);
        this._dragEnd = this._dragEnd.bind(this);
        this.openPopup = this.openPopup.bind(this);
        this.closePopup = this.closePopup.bind(this);
        
    }

    callApi = async ()=>{
        axios.get("http://117.16.164.14:5050/reserve").then((res)=> console.log(res));
    };

    componentDidMount(){
        this.callApi();
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
    handleProfileInput = (e) => {
        let reader = new FileReader();
        let profile = e.target.files[0];
        reader.onloadend = () => {
            this.setState({
                profile : profile,
                profileURL : reader.result
            })
        }
        reader.readAsDataURL(profile);
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
        localStorage.previewURL = JSON.stringify(this.state.previewURL);
        localStorage.id = JSON.stringify(this.state.id);
        localStorage.tables = JSON.stringify(this.state.tables);
        

        axios.post("http://117.16.164.14:5050/reserve", this.state.tables).then((res) => console.log(res));

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

    onChangetext = (e) => {
        this.setState({

        });
    }

	// 팝업창 열기
    openPopup() {
        this.setState({
            isOpenPopup: true
        })
    }

	// 팝업창 닫기
    closePopup() {
        this.setState({
            isOpenPopup: false
        })
    }



    render(){
        console.log(this.props.mode);
        console.log(this.state.reservation);
        console.log(this.state.tables);
        
        const {tables, reservation, customer} = this.state;
        if(this.props.mode === 'reservation'){
            return (
                <div id="container" className="container">
                    <div className="title">
                        <p>예약 현황</p>
                    </div>
                    {tables.map(table=> {
                    const match = reservation.filter(reserve => (reserve.table === table.id));
                    return (
                    <div className="current">
                        <h3 className="table_id">{table.id}</h3>
                        <ul>
                            {match.map(reserve => 
                                {return (
                                    <li>{reserve.time} {reserve.name}</li>
                                )})}
                        </ul>
                    </div>)})}
                </div>
            ) 
        }
        else if(this.props.mode === 'edit'){
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
                            {tables.map(table=>{
                            return(
                                <div
                                    className="table"
                                    id={table.id}
                                    style={{left:table.x, top:table.y}}
                                    onMouseDown={this._dragStart}
                                    onMouseMove={this._dragging}
                                    onMouseUp={this._dragEnd}
                                >{table.id}<button onClick={() => {this.delete(table.id);}}>x</button></div>
                            )})}
                        </div>
                    </div>
                    
                    
                    <div className="status"></div>
                </div>
            )
        }
        else if(this.props.mode === 'setup'){
            return(
                <div id="container" className="container">
                    <div className="title">
                        <p>설정</p>
                    </div>
                    <div>
                        <img className='profile_i' src={this.state.profileURL}/>
                        <br/>
                        <input type="file" name='profile' onChange={this.handleProfileInput}></input>
                    </div>
                    <div>
                        <p>ID: </p>
                        <p>가게명: <input onChange={this.onChangetext}/></p>
                        <p>가게주소: <input placeholder={this.state.setting.name} onClick={this.openPopup} readOnly/>
                            <button type='button' id="popupDom" onClick={this.openPopup}>주소찾기</button>
                            {this.state.isOpenPopup && 
                                <PopupDom>
                                    <PopupPostCode onClose={this.closePopup} />
                                </PopupDom>
                            }
                            </p>
                        <p>운영시간: </p>
                        <p>테이블 이용시간: </p>
                        <p>알람 설정</p>
                    </div>
                    
                </div>
            )
        }
    }
}

export default Container;
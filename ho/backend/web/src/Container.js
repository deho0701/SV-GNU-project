import React from 'react';
import Table from "./Table.js";

class Container extends React.Component{
    id=1;
    constructor(props){
        super(props);
        this.state = {
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
            previewURL: '',
            diffX: 0,
            diffY: 0,
            shopX: document.querySelector("#shop").offsetLeft,
            shopY: document.querySelector("#shop").offsetTop,
            dragging: false,
            styles: {left:0,
                    top:0}
        }

        this._dragStart = this._dragStart.bind(this);
        this._dragging = this._dragging.bind(this);
        this._dragEnd = this._dragEnd.bind(this);
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

    addTable = () => {
        const { tables } = this.state;
        this.setState({
            tables: tables.concat({id:this.id++, x:0, y:0})
        })
    }

    

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


    render(){
        console.log(this.props.mode);
        console.log(this.state.reservation);
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
                        {/*<div id="t_list" className="t_list"></div>*/}
                        <div id="shop" className="shop">
                            <p>Shop</p>
                            <img className='floor_img' src={this.state.previewURL}></img>
                            {tables.map(table=>{
                            return(
                                <div
                                    className="table"
                                    id={table.id}
                                    style={{left:table.x, top:table.y}}
                                    onMouseDown={this._dragStart}
                                    onMouseMove={this._dragging}
                                    onMouseUp={this._dragEnd}
                                >{table.id}</div>
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
                    
                </div>
            )
        }
    }
}

export default Container;
import React from 'react';
import Table from "./Table.js";

class Container extends React.Component{
    constructor(props){
        super(props)
        this.state = {
            tables: [
                {id:1, x:10, y:10},
                {id:2, x:20, y:25},
                {id:3, x:15, y:15}
            ],
            reservation: [
                {id:1, table:1, time:1300, name:'Yun'},
                {id:2, table:2, time:1500, name:'Gyu'},
                {id:3, table:1, time:2000, name:'Kim'}
            ],
            customer: [
                {cid:1, name:'Yun'}
    
            ]
        }
    }
    render(){
        console.log(this.props.mode);
        console.log(this.state.reservation);
        
        if(this.props.mode === 'reservation'){
            const {tables, reservation, customer} = this.state;
            return (
                <div id="container" className="container">
                    <div className="title">
                        <p>예약 현황</p>
                    </div>
                    {tables.map(table=>(
                    <Table
                    table={table.id}
                    reservation={reservation}
                    />))}
                </div>
            ) 
        }
        else if(this.props.mode === 'edit'){
            return(
                <div id="container" className="container">
                    <div className="title">
                        <p>자리 배치</p>
                    </div>
                    <button id="add">추가</button>
                    <input type="file" id="fileInput"/>
                    <button id="save">저장</button>
                    <div id="shop" className="shop">
                        <p>자리를 옮기세요</p>
                        <div id="table1" className="table">1</div>
                        <div id="table2" className="table">2</div>
                        <div id="table3"className="table">3</div>
                        </div>
                    <div className="status"></div>
                </div>
            )
        }
    }
}

export default Container;
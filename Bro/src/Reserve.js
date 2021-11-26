import React from 'react';
import axios from 'axios';

class Reserve extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            tables: [
                {id:1, x:10, y:10},
                {id:2, x:20, y:25},
                {id:3, x:15, y:15}
            ],
            reservation: [
                {id:1, table:1, time:'13:00', name:'Yun'},
                {id:2, table:2, time:'15:00', name:'Gyu'},
                {id:3, table:1, time:'20:00', name:'Kim'}
            ]
        }
    }

    callApi = async ()=>{
        const shopID = {};
        shopID.name = this.props.name;
        console.log(shopID);
        axios.post("http://117.16.164.14:5050/web/reserve", shopID).then((res)=> console.log(res));
    };

    componentDidMount(){
        console.log(this.props.name);
        this.callApi();
    }

    render(){
        console.log(this.state.reservation);
        console.log(this.props.name);
        const {tables, reservation} = this.state;
        return(
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
}

export default Reserve;
import React from 'react';

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

    render(){
        console.log(this.state.reservation);
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
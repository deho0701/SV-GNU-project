import React from 'react';
import axios from 'axios';

import Tippy from '@tippyjs/react';
import 'tippy.js/dist/tippy.css'

class Reserve extends React.Component{
    constructor(props){
        super(props);
        /****
        state 설명
        tables: 테이블 객체를 가져와 예약화면에 보여지는 테이블의 개수 결정
        reservation: 예약현황을 해당 테이블에 매치
        ****/
        this.state = {
            tables: [
                {table_id:1, x:10, y:10},
                {table_id:2, x:20, y:25},
                {table_id:3, x:15, y:15}
            ],
            reservation: [
                {table_id:1, time:1300, client_name:'Yun'}
            ]
        }
    }

    //post -> shopID, res->table, reservation data
    callApi = async ()=>{
        const shopID = {};
        shopID.name = this.props.name;
        console.log(shopID);
        axios.post("http://117.16.164.14:5050/web/reserve", {name:'cafe 502'})
            .then((res)=> {
                console.log(res.data);
                this.setState({
                    reservation: res.data
                })
            });
        axios.post("http://117.16.164.14:5050/web/reserve_tables", {name:'cafe 502'})
            .then((res)=> {
                console.log(res.data);
                this.setState({
                    tables: res.data
                })
            });
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
                    const match = reservation.filter(reserve => (reserve.table_id === table.table_id));
                    return (
                    <div className="current">
                        <h3 className="table_id">{table.table_id}</h3>
                        <ul>
                            {match.map(reserve => 
                                {
                                    const ColoredTooltip = () => {
                                        return <span style={{color: 'yellow'}}><p>사용자 정보</p><p>{reserve.time} </p><p>{reserve.client_name}</p></span>
                                    }
    
                                    return (
                                        <>
                                        <Tippy placement='right' content={<ColoredTooltip></ColoredTooltip>}>
                                            <li>{reserve.time} {reserve.client_name}</li>
                                        </Tippy>
                                        </>
                                )})}
                        </ul>
                    </div>)})}
                </div>
        )
    }
}

export default Reserve;
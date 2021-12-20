import React,{useState}  from 'react';
import styled from "styled-components";

import 'moment/locale/ko';

import 'react-toastify/dist/ReactToastify.css';

const Tab = styled.button`
    padding: 10px 30px;
    cursor: pointer;
    opacity: 0.6;
    background: white;
    border: 0;
    outline: 0;
    border-bottom: 2px solid transparent;
    transition: ease border-bottom 250ms;
    ${({ active }) =>
        active &&
        `
        border-bottom: 2px solid black;
        opacity: 1;
    `}
`;

const types2 = ["5", "10", "15", "20", "25"];

const TabGroup2 = (props) => {
    var data = props.data;
    console.log(data);
    var active2 = data;
    console.log(active2);
    const sendTime = (type2) => {
        active2=type2;
        props.onChange(type2);
    }

    return (
    <>
        <p> 알람설정 : {active2}분 전 </p>
        
        <div>
            {types2.map((type2) => (
            <Tab
                key={type2}
                active={type2 == active2}
                onClick={() => sendTime(type2)}
            >
            {type2}
            </Tab>
            ))}
        </div>
        <p/>
    </>
    );
}

export default TabGroup2;
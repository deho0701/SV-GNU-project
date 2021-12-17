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

const types1 = ["1", "2", "3", "4", "5"];

const TabGroup = (props) => {
    var data = props.data;
    console.log(data);
    var active1 = data;
    console.log(active1);
    const sendTime = (type) => {
        active1=type;
        props.onChange(type);
    }

    return (
    <>
        <p> 테이블 이용시간 : 최대 {active1}시간 </p>
        
        <div>
            {types1.map((type) => (
            <Tab
                key={type}
                active={type == active1}
                onClick={() => sendTime(type)}
            >
            {type}
            </Tab>
            ))}
        </div>
        <p/>
    </>
    );
}

export default TabGroup;
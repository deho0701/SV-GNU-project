import React,{useState}  from 'react';

import moment from 'moment';
import Moment from 'react-moment';
import 'moment/locale/ko';

import { ToastContainer, toast } from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import { useInterval } from 'react-use';
import { createRoutesFromChildren } from 'react-router';

const customId = "custom-id-yes";
const LiveTimeContainer = () => {
    const [seconds, setSeconds] = useState(Date.now());
    
    const notify = () => {
        toast("왜 안되는지 모르겠다!!",{
        toastId: customId,
        position: "bottom-right",
        autoClose: 50000
    })};
    // useInterval
    useInterval(() => {
        setSeconds(Date.now());
    }, 1000);

    const startTime = new Date('2021-11-17T15:11'),
        nowTimeFormat = new Date(seconds);
    const count =1;
    return (
    <>
    {(((startTime - nowTimeFormat) > 0)) ? 
        (<>알람 예정</>) : (<>{notify()}<ToastContainer/></>)
    }  


    </>
    )
}

export default LiveTimeContainer;
import React from 'react';
import profileimg from "./cafe502.png";
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';
import Tab from './Tab';
import axios from 'axios';
import TabGroup from './Tab';
import TabGroup2 from './Tab2';

class Setting extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            store_ID:'',
            name:'',
            address:'주소를 입력하세요',
            profile: '',
            profileURL: profileimg,
            start_time: '13:00',
            end_time: '',
            tableTime: '',
            alarmTime: 5,
            isOpenPopup : false,
        }

        this.openPopup = this.openPopup.bind(this);
        this.closePopup = this.closePopup.bind(this);
        this.changeSTime = this.changeSTime.bind(this);
        this.changeETime = this.changeETime.bind(this);
        this.changeTableTime = this.changeTableTime.bind(this);
        this.changeAlarmTime = this.changeAlarmTime.bind(this);
    }

    callApi = async ()=>{
        await axios.post("http://117.16.164.14:5050/web/pre_setting", {name:'cafe 502'}
        ).then((res)=>{
            console.log(res.data[0])
            this.setState({
                store_ID: res.data[0].store_ID,
                name: res.data[0].store_name,
                address: res.data[0].address,
                start_time: res.data[0].start_time,
                end_time: res.data[0].end_time,
                tableTime: res.data[0].table_time,
                alarmTime: res.data[0].alarm_time
            })}
        );
        
    };

    componentDidMount(){
        this.callApi();
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

    onChangetext = (e) => {
        this.setState({
            name: e.target.value
        });
    }

	// 팝업창 열기
    openPopup() {
        this.setState({
            isOpenPopup: true
        })
    }

	// 팝업창 닫기
    closePopup(data) {
        console.log(typeof(data));
        this.setState({
            isOpenPopup: false,
            address: data
        })
    }

    changeSTime(e){
        this.setState({
            start_time: e.target.value
        })
    }

    changeETime(e){
        this.setState({
            end_time: e.target.value
        })
    }

    changeTableTime(data) {
        console.log(data);
        this.setState({
            tableTime: data
        })
    }

    changeAlarmTime(data) {
        console.log(data);
        this.setState({
            alarmTime: data
        })
    }

    save = () => {
        const formData = new FormData();
        const config = {
            header: {'content-type': 'multipart/form-data'}
        }
        
        var data = {};
        data.name = this.props.name;
        data.address = this.state.address;
        data.start_time = this.state.start_time;
        data.end_time = this.state.end_time;
        data.table_time = this.state.tableTime;
        data.alarm_time = this.state.alarmTime;

        axios.post("http://117.16.164.14:5050/web/setting", data).then((res)=>console.log(res));
    }

    render(){
        console.log(this.state.name);
        console.log(this.state.tableTime);
        return(
            <div id="container" className="container">
                <div className="title">
                    <p>설정</p>
                </div>
                <div className="grid">
                    <div>
                        <img className='profile_i' src={this.state.profileURL}/>
                        <br/>
                        <input type="file" className='imgProfile' name='profile' onChange={this.handleProfileInput}></input>
                    </div>
                    <div className="profile_container">
                        <p>ID: {this.state.store_ID}</p>
                        <p>가게명: <input className="input_setting" value={this.state.name} onChange={this.onChangetext}/></p>
                        <p>가게주소: <input className="input_setting" value={this.state.address} onClick={this.openPopup} readOnly/>
                            <button type='button' id="popupDom" onClick={this.openPopup}>주소찾기</button>
                            {this.state.isOpenPopup && 
                                <PopupDom>
                                    <PopupPostCode onClose={this.closePopup} />
                                </PopupDom>
                            }
                        </p>
                        <p>운영시간: <input type='time' className="input_setting" value={this.state.start_time} onChange={this.changeSTime}/>
                            ~<input type='time' className="input_setting" value={this.state.end_time} onChange={this.changeETime}/></p>
                        <p><TabGroup onChange={this.changeTableTime} data={this.state.tableTime}/></p>
                        <p><TabGroup2 onChange={this.changeAlarmTime} data={this.state.alarmTime}/></p>
                        <button id="save_btn" className='btn_style' onClick={this.save}>저장</button>
                    </div>
                </div>
            </div>
        )
    }
}

export default Setting;
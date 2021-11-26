import React from 'react';
import img from './sample1.JPG';
import Reserve from './Reserve.js';
import Edit from './Edit.js';
import Setting from './Setting';
import PopupDom from './PopupDom';
import PopupPostCode from './PopupPostCode';
import axios from 'axios';

class Container extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            
            address:'주소를 입력하세요',
            
            customer: [
                {cid:1, name:'Yun'}
            ],
            profile: '',
            profileURL: '',
            
            isOpenPopup : false
        }

        
        this.openPopup = this.openPopup.bind(this);
        this.closePopup = this.closePopup.bind(this);
        
    }

    callApi = async ()=>{
        axios.get("http://117.16.164.14:5050/reserve").then((res)=> console.log(res));
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



    render(){
        console.log(this.props.mode);
        
        const {tables, reservation, customer} = this.state;
        if(this.props.mode === 'reservation'){
            return (
                <Reserve/>
            ) 
        }
        else if(this.props.mode === 'edit'){
            return(
                <Edit/>
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
                        <p>가게주소: <input placeholder={this.state.address} onClick={this.openPopup} readOnly/>
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
import React from 'react';
import Reserve from './Reserve.js';
import Edit from './Edit.js';
import Setting from './Setting.js';
import { Beforeunload } from 'react-beforeunload';
import axios from 'axios';

class Container extends React.Component{
    constructor(props){
        super(props);
        this.state = {
            
        }
    }

    callApi = async ()=>{
        //axios.get("http://117.16.164.14:5050/ok_seat").then((res)=> console.log(res));
    };

    componentDidMount(){
        //this.callApi();
    }

    render(){
        console.log(this.props.mode);
        if(this.props.mode === 'reservation'){
            return (
                <Reserve name={this.props.name}/>
            ) 
        }
        else if(this.props.mode === 'edit'){
            return(
                <Edit name={this.props.name}/>
            )
        }
        else if(this.props.mode === 'setup'){
            return(
                <Setting name={this.props.name}/>
            )
        }
    }
}

export default Container;
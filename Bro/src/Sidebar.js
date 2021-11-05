import React from 'react';

class Sidebar extends React.Component{
    render(){
        return(
            <div className="sidebar">
                <div className="profile">
                    <div className="box">
                        <img className="profile-img" src={this.props.img} alt = 'profile' title='profile'/>
                    </div>
                    <p>{this.props.name}</p>
                </div>
                <ul>
                    <li>
                        <a 
                            href="/"
                            data-mode='reservation'
                            onClick={function(e){
                                e.preventDefault();
                                this.props.onChangePage(e.target.dataset.mode);
                            }.bind(this)}
                            >예약 현황</a>
                    </li>
                    <li><a 
                        href="/"
                        data-mode='edit'
                            onClick={function(e){
                                e.preventDefault();
                                this.props.onChangePage(e.target.dataset.mode);
                            }.bind(this)}>자리 배치</a>
                    </li>
                    <li><a 
                        href="/"
                        data-mode='setup'
                            onClick={function(e){
                                e.preventDefault();
                                this.props.onChangePage(e.target.dataset.mode);
                            }.bind(this)}>설정</a>
                    </li>
                </ul>
            </div>
        )
    }
}
export default Sidebar;
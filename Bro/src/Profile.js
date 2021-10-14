import React from 'react';
import img from "./sample.JPG";

function Profile(){
  return (
    <div className="profile">
      <div className="box">
        <img className="profile-img" src={img} alt = 'profile' title='profile'/>
      </div>
      <p>Shop</p>
    </div>
  )
}

export default Profile;
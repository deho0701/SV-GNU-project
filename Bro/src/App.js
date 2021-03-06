import React from 'react';
import img from "./icon.png";
import "./App.css";
import Sidebar from "./Sidebar";
import Container from "./Container";



class App extends React.Component {
  constructor(props){
    super(props)
    this.state = {
      shop: {name:'cafe 502', img:img},
      mode: 'reservation'
    }  
  };

  render() {
    return (
      <div className="wrapper">
        <Sidebar
          name={this.state.shop.name}
          img={this.state.shop.img}
          onChangePage={function(mode){
            this.setState({mode:mode})
          }.bind(this)}
        />
        <Container
          name={this.state.shop.name}
          mode={this.state.mode}
        />
      </div>
    )
  }
}

export default App;

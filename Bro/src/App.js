import React from 'react';
import img from "./sample.JPG";
import "./App.css";
import Sidebar from "./Sidebar";
import Container from "./Container";



class App extends React.Component {
  constructor(props){
    super(props)
    this.state = {
      Shop: {name:'북카페', img:img},
      mode: 'reservation'
    }
    
  }

  componentWillMount(){

  }

  render() {
    
    console.log(this.state.Shop.name);
    return (
      <div className="wrapper">
        <Sidebar
          name={this.state.Shop.name}
          img={this.state.Shop.img}
          onChangePage={function(mode){
            this.setState({mode:mode})
          }.bind(this)}
        />
        <Container
          mode={this.state.mode}
        />
      </div>
    )
  }
}

export default App;

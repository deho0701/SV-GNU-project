import React from 'react';

class Table extends React.Component{
    render() {
        
        const match = this.props.reservation.filter(reserve => (reserve.table === this.props.table));
        console.log(match);
        return(
            <div className="current">
                <h3 className="table_id">{this.props.table}</h3>
                <ul>
                    {match.map(reserve => 
                        {return (
                            <li>{reserve.time} {reserve.name}</li>
                        )})}
                </ul>
            </div>       
        )
    }
}

export default Table;
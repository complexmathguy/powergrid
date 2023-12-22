import React, { Component } from 'react'
import SwitchService from '../services/SwitchService'

class ListSwitchComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
                switchs: []
        }
        this.addSwitch = this.addSwitch.bind(this);
        this.editSwitch = this.editSwitch.bind(this);
        this.deleteSwitch = this.deleteSwitch.bind(this);
    }

    deleteSwitch(id){
        SwitchService.deleteSwitch(id).then( res => {
            this.setState({switchs: this.state.switchs.filter(switch => switch.switchId !== id)});
        });
    }
    viewSwitch(id){
        this.props.history.push(`/view-switch/${id}`);
    }
    editSwitch(id){
        this.props.history.push(`/add-switch/${id}`);
    }

    componentDidMount(){
        SwitchService.getSwitchs().then((res) => {
            this.setState({ switchs: res.data});
        });
    }

    addSwitch(){
        this.props.history.push('/add-switch/_add');
    }

    render() {
        return (
            <div>
                 <h2 className="text-center">Switch List</h2>
                 <div className = "row">
                    <button className="btn btn-primary btn-sm" onClick={this.addSwitch}> Add Switch</button>
                 </div>
                 <br></br>
                 <div className = "row">
                        <table className = "table table-striped table-bordered">

                            <thead>
                                <tr>
                                    <th> Open </th>
                                    <th> Actions</th>
                                </tr>
                            </thead>
                            <tbody>
                                {
                                    this.state.switchs.map(
                                        switch => 
                                        <tr key = {switch.switchId}>
                                             <td> { switch.open } </td>
                                             <td>
                                                 <button onClick={ () => this.editSwitch(switch.switchId)} className="btn btn-info btn-sm">Update </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.deleteSwitch(switch.switchId)} className="btn btn-danger btn-sm">Delete </button>
                                                 <button style={{marginLeft: "10px"}} onClick={ () => this.viewSwitch(switch.switchId)} className="btn btn-info btn-sm">View </button>
                                             </td>
                                        </tr>
                                    )
                                }
                            </tbody>
                        </table>

                 </div>

            </div>
        )
    }
}

export default ListSwitchComponent

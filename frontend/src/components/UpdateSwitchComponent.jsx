import React, { Component } from 'react'
import SwitchService from '../services/SwitchService';

class UpdateSwitchComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
                open: ''
        }
        this.updateSwitch = this.updateSwitch.bind(this);

        this.changeopenHandler = this.changeopenHandler.bind(this);
    }

    componentDidMount(){
        SwitchService.getSwitchById(this.state.id).then( (res) =>{
            let switch = res.data;
            this.setState({
                open: switch.open
            });
        });
    }

    updateSwitch = (e) => {
        e.preventDefault();
        let switch = {
            switchId: this.state.id,
            open: this.state.open
        };
        console.log('switch => ' + JSON.stringify(switch));
        console.log('id => ' + JSON.stringify(this.state.id));
        SwitchService.updateSwitch(switch).then( res => {
            this.props.history.push('/switchs');
        });
    }

    changeopenHandler= (event) => {
        this.setState({open: event.target.value});
    }

    cancel(){
        this.props.history.push('/switchs');
    }

    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                <h3 className="text-center">Update Switch</h3>
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> open: </label>
                                            #formFields( $attribute, 'update')
                                        </div>
                                        <button className="btn btn-success" onClick={this.updateSwitch}>Save</button>
                                        <button className="btn btn-danger" onClick={this.cancel.bind(this)} style={{marginLeft: "10px"}}>Cancel</button>
                                    </form>
                                </div>
                            </div>
                        </div>

                   </div>
            </div>
        )
    }
}

export default UpdateSwitchComponent

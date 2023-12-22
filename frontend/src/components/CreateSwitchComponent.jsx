import React, { Component } from 'react'
import SwitchService from '../services/SwitchService';

class CreateSwitchComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            // step 2
            id: this.props.match.params.id,
                open: ''
        }
        this.changeopenHandler = this.changeopenHandler.bind(this);
    }

    // step 3
    componentDidMount(){

        // step 4
        if(this.state.id === '_add'){
            return
        }else{
            SwitchService.getSwitchById(this.state.id).then( (res) =>{
                let switch = res.data;
                this.setState({
                    open: switch.open
                });
            });
        }        
    }
    saveOrUpdateSwitch = (e) => {
        e.preventDefault();
        let switch = {
                switchId: this.state.id,
                open: this.state.open
            };
        console.log('switch => ' + JSON.stringify(switch));

        // step 5
        if(this.state.id === '_add'){
            switch.switchId=''
            SwitchService.createSwitch(switch).then(res =>{
                this.props.history.push('/switchs');
            });
        }else{
            SwitchService.updateSwitch(switch).then( res => {
                this.props.history.push('/switchs');
            });
        }
    }
    
    changeopenHandler= (event) => {
        this.setState({open: event.target.value});
    }

    cancel(){
        this.props.history.push('/switchs');
    }

    getTitle(){
        if(this.state.id === '_add'){
            return <h3 className="text-center">Add Switch</h3>
        }else{
            return <h3 className="text-center">Update Switch</h3>
        }
    }
    render() {
        return (
            <div>
                <br></br>
                   <div className = "container">
                        <div className = "row">
                            <div className = "card col-md-6 offset-md-3 offset-md-3">
                                {
                                    this.getTitle()
                                }
                                <div className = "card-body">
                                    <form>
                                        <div className = "form-group">
                                            <label> open: </label>
                                            #formFields( $attribute, 'create')
                                        </div>

                                        <button className="btn btn-success" onClick={this.saveOrUpdateSwitch}>Save</button>
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

export default CreateSwitchComponent

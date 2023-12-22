import React, { Component } from 'react'
import SwitchService from '../services/SwitchService'

class ViewSwitchComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            switch: {}
        }
    }

    componentDidMount(){
        SwitchService.getSwitchById(this.state.id).then( res => {
            this.setState({switch: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View Switch Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> open:&emsp; </label>
                            <div> { this.state.switch.open }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewSwitchComponent

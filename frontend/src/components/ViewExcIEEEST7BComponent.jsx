import React, { Component } from 'react'
import ExcIEEEST7BService from '../services/ExcIEEEST7BService'

class ViewExcIEEEST7BComponent extends Component {
    constructor(props) {
        super(props)

        this.state = {
            id: this.props.match.params.id,
            excIEEEST7B: {}
        }
    }

    componentDidMount(){
        ExcIEEEST7BService.getExcIEEEST7BById(this.state.id).then( res => {
            this.setState({excIEEEST7B: res.data});
        })
    }

    render() {
        return (
            <div>
                <br></br>
                <div className = "card col-md-6 offset-md-3">
                    <h3 className = "text-center"> View ExcIEEEST7B Details</h3>
                    <div className = "card-body">
                        <div className = "row">
                            <label> kh:&emsp; </label>
                            <div> { this.state.excIEEEST7B.kh }</div>
                        </div>
                        <div className = "row">
                            <label> kia:&emsp; </label>
                            <div> { this.state.excIEEEST7B.kia }</div>
                        </div>
                        <div className = "row">
                            <label> kl:&emsp; </label>
                            <div> { this.state.excIEEEST7B.kl }</div>
                        </div>
                        <div className = "row">
                            <label> kpa:&emsp; </label>
                            <div> { this.state.excIEEEST7B.kpa }</div>
                        </div>
                        <div className = "row">
                            <label> oelin:&emsp; </label>
                            <div> { this.state.excIEEEST7B.oelin }</div>
                        </div>
                        <div className = "row">
                            <label> tb:&emsp; </label>
                            <div> { this.state.excIEEEST7B.tb }</div>
                        </div>
                        <div className = "row">
                            <label> tc:&emsp; </label>
                            <div> { this.state.excIEEEST7B.tc }</div>
                        </div>
                        <div className = "row">
                            <label> tf:&emsp; </label>
                            <div> { this.state.excIEEEST7B.tf }</div>
                        </div>
                        <div className = "row">
                            <label> tg:&emsp; </label>
                            <div> { this.state.excIEEEST7B.tg }</div>
                        </div>
                        <div className = "row">
                            <label> tia:&emsp; </label>
                            <div> { this.state.excIEEEST7B.tia }</div>
                        </div>
                        <div className = "row">
                            <label> uelin:&emsp; </label>
                            <div> { this.state.excIEEEST7B.uelin }</div>
                        </div>
                        <div className = "row">
                            <label> vmax:&emsp; </label>
                            <div> { this.state.excIEEEST7B.vmax }</div>
                        </div>
                        <div className = "row">
                            <label> vmin:&emsp; </label>
                            <div> { this.state.excIEEEST7B.vmin }</div>
                        </div>
                        <div className = "row">
                            <label> vrmax:&emsp; </label>
                            <div> { this.state.excIEEEST7B.vrmax }</div>
                        </div>
                        <div className = "row">
                            <label> vrmin:&emsp; </label>
                            <div> { this.state.excIEEEST7B.vrmin }</div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}

export default ViewExcIEEEST7BComponent

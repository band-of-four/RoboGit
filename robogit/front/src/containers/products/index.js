import React, {Component} from 'react'

class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {products: []};
    }
    componentDidMount () {
        $.ajax({
            url: "http://localhost:8080/api/information/1"
        }).then(function (data) {
            console.log("data = " + data);
            this.setState({products: data});
        });
    }
    render() {
        return (
            <div>
                {this.state.products.map(item => <p>item.id</p>)}
            </div>
        )
    }
}
export default Products
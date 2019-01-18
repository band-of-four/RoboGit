import React, {Component} from 'react'

class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {products: []};
    }
    componentDidMount () {
        $.ajax({
            url: "http://localhost:8080/api/information_top"
        }).then(function (data) {
            console.log(data);
            this.setState({products: data});
        });
    }
    render() {
        return (
            <div>
                {this.state.products}
            </div>
        )
    }
}
export default Products
import React, {Component} from 'react'



class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {products: []};
    }
    componentDidMount () {
        console.log("request");
        var reactThis = this;
        $.ajax({
            url: "http://robogit.org:8080/api/information"
        }).then(function (data) {
            console.log("data = " + data);
            reactThis.setState({products: data});
            console.log("data = " + this.state);
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
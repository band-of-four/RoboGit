import React, {Component} from 'react'



class Products extends Component {
    constructor(props) {
        super(props);
        this.state = {products: []};
    }
    componentDidMount () {
        console.log("request");
        var reactThis = this;

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
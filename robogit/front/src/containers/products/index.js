import React, {Component} from 'react'

class Products extends Component {
  constructor(props) {
    super(props);
    this.state = {products: []};
  }

  componentDidMount() {
    let product = this;
    $.ajax({
      url: "http://robogit.org:8080/api/information"
    }).then(function (data) {
      console.log("data = " + data);
      product.setState({products: data});
    }).catch(() => console.log("Can’t access /api/information response"));
  }

  render() {
    return (
      <div>
        {this.state.products.map(item => <p>{item.information.name}</p>)}
      </div>
    )
  }
}

export default Products

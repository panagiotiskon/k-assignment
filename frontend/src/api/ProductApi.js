import axios from "axios";

const API_URL = "http://localhost:8080/app";

axios.defaults.withCredentials = true;

const getAllProducts = (filter = {}, page = 0, size = 10) => {
    console.log(page, size);
    const queryParams = new URLSearchParams();

    queryParams.append("page", page);
    queryParams.append("size", size);

    if (filter.companies && Array.isArray(filter.companies)) {
        filter.companies.forEach(company => queryParams.append("company", company));
    }

    if (filter.minPrice !== undefined) {
        queryParams.append("minPrice", filter.minPrice);
    }
    
    if (filter.maxPrice !== undefined) {
        queryParams.append("maxPrice", filter.maxPrice);
    }

    const url = `${API_URL}/products?${queryParams.toString()}`;
    console.log(url);
    return axios
        .get(url)
        .then(response => {
            return response.data;
        })
        .catch(error => {
            console.error("Error while fetching products");
            throw error;
        });
};

const createProduct = (productData) => {
    return axios
        .post(`${API_URL}/products`, productData)
        .then(response => response.data)
        .catch(error => {
            console.error("Error while creating product:", error);
            throw error;
        });
};

const updateProduct = (productId, productData) => {
    return axios 
        .put(`${API_URL}/products/${productId}`, productData)
        .then(response => response.data)
        .catch(error => {
            console.error("Error while updating product:", error);
            throw error;
        });
}


const deleteProduct = (productId) => {
    return axios
        .delete(`${API_URL}/products/${productId}`)
        .then(response => response.data)
        .catch(error=>{
            console.error("Error while deleting product", error)
            throw error;
        })
}

const getProductCompanies = () =>{
    return axios
        .get(`${API_URL}/products/companies`)
        .then(response => response.data)
        .catch(error=>{
            console.error("Error while fetching product companies", error); 
            throw error; 
        })
}

const ProductService = {
    getAllProducts, 
    createProduct, 
    updateProduct,
    deleteProduct, 
    getProductCompanies
};

export default ProductService;

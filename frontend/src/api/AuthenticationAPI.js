import axios from "axios";

const API_URL = "http://localhost:8080"

axios.defaults.withCredentials = true;

const login = (email,password) => {
    return axios
        .post(API_URL+"/login", {email, password})
        .then((response)=>{
            return response.data;
        })
        .catch((error) => {
            console.error("Login Error:", error.message);
            console.error("Error Config:", error.config);
            throw error;
        })
}

const AuthService = {
    login
}

export default AuthService;
import axios from "axios";

const API_URL = "http://localhost:8080/auth"

axios.defaults.withCredentials = true;

const getCurrentUser = async () => {
    try {
        const response = await axios.get(`${API_URL}/current-user`, {
            withCredentials: true,
        });
        console.log(response);
        return response.data;
    } catch (error) {
        return null;
    }
};

const login = (email,password) => {
    console.log(email);
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

const logout = () => {
    return axios
        .post(API_URL+"/logout")
    .then((response)=>{
        return response.data;
    })    
}

const AuthService = {
    login, 
    logout, 
    getCurrentUser
}

export default AuthService;
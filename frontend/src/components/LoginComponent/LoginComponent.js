import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import {
    MDBContainer,
    MDBSpinner,
    MDBBtn,
    MDBInput,
    MDBCheckbox,
} from "mdb-react-ui-kit";
import "./LoginComponent.scss";
import passwordNotVisible from "../../assets/password-not-visible.png";
import passwordVisible from "../../assets/password-visible.png";
import AuthService from "../../api/AuthenticationAPI";

const LoginComponent = () => {
    const [email, setEmail] = useState("");
    const [password, setPassword] = useState("");
    const [showPassword, setShowPassword] = useState(false);
    const [rememberMe, setRememberMe] = useState(false);
    const navigate = useNavigate();
    const [loading, setLoading] = useState(false);
    const [message, setMessage] = useState("");

    const handleLogin = (e) => {

        e.preventDefault();
        setLoading(true); 

        AuthService.login(email, password).then(
            (response) => {
                if (response.roles[0].name === "ROLE_ADMIN") {
                    navigate("/home");
                }
            }, (error) => {
                setLoading(false);
                let resMessage = "";

                if (error.response && error.response.status === 401) {
                    resMessage = "Invalid email or password. Please try again.";
                } else {
                    resMessage =
                        (error.response &&
                            error.response.data &&
                            error.response.data.message) ||
                        error.message ||
                        error.toString();
                }

                setMessage(resMessage);
            }
        );
    }
    return (
        <div className="login-wrapper">
            <MDBContainer className="my-5 d-flex flex-column align-items-center mb-4">
                <h4 className="mb-4f" style={{
                    color: "black",
                    marginBottom: "20px",
                    fontWeight: "bold"
                }}>
                    Συνδέσου στον λογαριασμό σου</h4>
                <form onSubmit={handleLogin} style={{ width: "50%", maxWidth: "400px" }}>
                    <MDBInput
                        className="mb-4"
                        label="Email"
                        type="email"
                        style={{
                            height: "2.5rem"
                        }}
                        value={email}
                        onChange={(e) => setEmail(e.target.value)}
                        required
                    />
                    <MDBInput
                        className="mb-2"
                        label="Κωδικός πρόσβασης"
                        type={showPassword ? "text" : "password"}
                        value={password}
                        style={{
                            height: "2.5rem",
                        }}
                        onChange={(e) => setPassword(e.target.value)}
                        required
                    />
                    <div className="d-flex justify-content-between mb-4 pt-0">
                        <MDBCheckbox
                            name="rememberMe"
                            label="Να με θυμάσαι"
                            checked={rememberMe}
                            onChange={() => setRememberMe(!rememberMe)}
                        />
                        <button
                            type="button"
                            onClick={() => setShowPassword(!showPassword)}
                            style={{
                                background: "none",
                                border: "none",
                                cursor: "pointer",
                            }}
                        >
                            <img
                                src={showPassword ? passwordVisible : passwordNotVisible}
                                alt={showPassword ? "Hide password" : "Show password"}
                                style={{ width: "25px", height: "25px" }}
                            />
                        </button>
                    </div>

                    {message && (
                        <div className="alert alert-danger" role="alert">
                            {message}
                        </div>
                    )}

                    <MDBBtn type="submit" className="w-100 mb-4" disabled={loading} style={{ backgroundColor: "black", textTransform: "unset", fontSize: "16px" }}>
                        {loading ? <MDBSpinner small role="status" tag="span" /> : "Σύνδεση"}
                    </MDBBtn>

                    <div className="text-center" style={{ color: "black" }}>
                        Ξέχασες τον κωδικό σου; Πάτα 
                        <a href="/forgot-password" style={{ color: "black", textDecoration: "underline",marginLeft: "5px" }}>
                            εδώ
                        </a>
                    </div>
                </form>
            </MDBContainer>
        </div>
    );
};



export default LoginComponent;

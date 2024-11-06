import { useNavigate } from "react-router-dom";
import KotsovolosLogo from '../../../assets/kotsovolos.png';
import {
    MDBContainer,
    MDBNavbar,
    MDBBtn,
    MDBIcon,
} from "mdb-react-ui-kit";
import AuthService from "../../../api/AuthenticationAPI";
import { FontAwesomeIcon } from '@fortawesome/react-fontawesome';
import { faSignOutAlt } from '@fortawesome/free-solid-svg-icons';
import './NavBar.scss'

const NavBarComponent = () => {
    const navigate = useNavigate();

    const handleLogoutClick = () => {
        AuthService.logout();
        navigate("/");
    };

    return (
        <MDBNavbar expand="lg" style={{ backgroundColor: "#c52d26", maxHeight:"5rem" }}>
            <MDBContainer fluid className="navbar-container">
                {/* Logo */}
                <img
                    src={KotsovolosLogo}
                    alt="Kotsovolos-logo"
                    className="navbar-logo"
                />
                
                {/* Logout Button */}
                <MDBBtn
                    style={{
                        backgroundColor: "black", 
                        color: "white",
                        padding: "6px 12px" }}
                    className="logout-btn d-flex align-items-center"
                    onClick={handleLogoutClick}
                >
                    <MDBIcon fas icon="sign-out-alt" className="me-2" />
                    <FontAwesomeIcon icon={faSignOutAlt} />

                </MDBBtn>
            </MDBContainer>
        </MDBNavbar>
    );
};

export default NavBarComponent;

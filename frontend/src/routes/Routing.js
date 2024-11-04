import {createBrowserRouter} from "react-router-dom";
import Login from "../pages/Login";
import Home from "../pages/Home";
import ProtectedRoute from "./ProtectedRoute";

export const router = createBrowserRouter([
    {
        path:"/",
        element:<Login/>,
    },
    {
        path: "/home",
        element: <ProtectedRoute element={Home} allowedRoles={["ROLE_ADMIN", "ROLE_CLIENT"]} />,
    }
])

import { Navigate } from "react-router-dom";

export default function AdminRoute({ children }) {
    const logged = localStorage.getItem("adminLogged") === "true";

    return logged ? children : <Navigate to="/login" />;
}
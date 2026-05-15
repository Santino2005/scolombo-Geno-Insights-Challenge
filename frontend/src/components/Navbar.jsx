import { Link, useNavigate } from "react-router-dom";

export default function Navbar() {
    const navigate = useNavigate();

    const isGuard =
        localStorage.getItem("guardLogged") === "true";

    const isAdmin =
        localStorage.getItem("adminLogged") === "true";

    function logout() {
        localStorage.removeItem("guardLogged");
        localStorage.removeItem("adminLogged");
        navigate("/login");
    }

    return (
        <nav className="navbar">
            {isGuard && (
                <Link to="/guard">Guard</Link>
            )}

            <button onClick={logout}>
                Salir
            </button>
        </nav>
    );
}
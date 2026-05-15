import { BrowserRouter, Navigate, Route, Routes } from "react-router-dom";
import LoginPage from "./pages/LoginPage.jsx";
import VisitorPage from "./pages/VisitorPage";
import GuardPage from "./pages/GuardPage";
import AdminPage from "./pages/AdminPage";
import PrivateRoute from "./components/PrivateRoute.jsx";
import AdminRoute from "./components/AdminRoute.jsx";
import "./styles/App.css";

export default function App() {
    return (
        <BrowserRouter>
            <Routes>
                <Route path="/login" element={<LoginPage />} />

                <Route
                    path="/admin"
                    element={
                        <AdminRoute>
                            <AdminPage />
                        </AdminRoute>
                    }
                />

                <Route
                    path="/visitor"
                    element={<VisitorPage />}
                />

                <Route
                    path="/guard"
                    element={
                        <PrivateRoute>
                            <GuardPage />
                        </PrivateRoute>
                    }
                />

                <Route path="*" element={<Navigate to="/login" />} />
            </Routes>
        </BrowserRouter>
    );
}
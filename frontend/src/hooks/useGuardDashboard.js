import { useEffect, useState } from "react";
import {
    getTodayVisits,
    getVisitHistory,
    downloadVisitHistoryExcel,
} from "../api/visitApi";
import { countVisitors } from "../api/visitorApi";

export function useGuardDashboard() {
    const [todayCount, setTodayCount] = useState(0);
    const [totalVisitors, setTotalVisitors] = useState(0);
    const [history, setHistory] = useState([]);

    useEffect(() => {
        loadDashboard();
    }, []);

    async function loadDashboard() {
        try {
            const today = await getTodayVisits();
            const historyData = await getVisitHistory();
            const total = await countVisitors();

            setTodayCount(today.data.length);
            setHistory(historyData.data);
            setTotalVisitors(total.data);
        } catch (error) {
            console.error("Dashboard error:", error);
            console.error("Response:", error.response?.data);
            console.error("Status:", error.response?.status);
        }
    }

    async function downloadExcel() {
        const response = await downloadVisitHistoryExcel();

        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement("a");

        link.href = url;
        link.setAttribute("download", "visit-history.xlsx");
        document.body.appendChild(link);
        link.click();

        link.remove();
        window.URL.revokeObjectURL(url);
    }

    return {
        todayCount,
        totalVisitors,
        history,
        loadDashboard,
        downloadExcel,
    };
}
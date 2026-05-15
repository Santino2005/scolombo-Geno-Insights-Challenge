import Metrics from "./Metrics";
import VisitHistoryTable from "./VisitHistoryTable";

export default function DashboardPanel({
                                           todayCount,
                                           totalVisitors,
                                           history,
                                           onDownloadExcel,
                                       }) {
    return (
        <section className="dashboard-section">
            <h3 className="dashboard-title">Dashboard</h3>

            <Metrics
                todayCount={todayCount}
                totalVisitors={totalVisitors}
            />

            <button className="wide-button" onClick={onDownloadExcel}>
                📥 Descargar Excel
            </button>

            <div className="history-table-wrapper">
                <VisitHistoryTable history={history} />
            </div>
        </section>
    );
}
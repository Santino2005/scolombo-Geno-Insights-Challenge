export default function Metrics({ todayCount, totalVisitors }) {
    return (
        <div className="metrics">
            <div className="metric">
                <strong>{todayCount}</strong>
                <span>INGRESOS HOY</span>
            </div>

            <div className="metric">
                <strong>{totalVisitors}</strong>
                <span>TOTAL REGISTRADOS</span>
            </div>
        </div>
    );
}
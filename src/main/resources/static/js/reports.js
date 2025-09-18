validateUserRole("admin");
let loanDistributionChart;

window.addEventListener('load', loadDashboardData);

let statusSummaryChart;

async function loadDashboardData() {
  try {
      const response = await fetch('/api/reports/dashboard-data');
      if (!response.ok) {
          throw new Error('Failed to fetch dashboard data');
      }
      const data = await response.json();

      // Update Key Metrics
      document.getElementById('approvalRate').textContent = `${(data.approvalRate).toFixed(2)}%`;
      document.getElementById('avgLoanAmount').textContent = `₹${(data.avgLoanAmount).toFixed(2)}`;
      document.getElementById('activeCustomers').textContent = data.activeCustomers;
      document.getElementById('activeProducts').textContent = data.activeProducts;
      // The following lines are removed as the corresponding elements were removed from the HTML
      // document.getElementById('totalRepaymentsMade').textContent = data.totalRepaymentsMade;
      // document.getElementById('remainingRepayments').textContent = data.remainingRepayments;

      // Update Charts
      updateLoanDistributionChart(data.loanDistribution);
      updateStatusSummaryChart(data);

      // Update Tables
      updateTopProductsList(data.loanDistribution);
      updateMonthlyPerformanceTable(data.monthlyPerformance);

  } catch (error) {
      console.error('Error loading dashboard data:', error);
      alert('Failed to load dashboard data. Please check the backend connection.');
  }
}

function updateLoanDistributionChart(loanData) {
  if (loanDistributionChart) { loanDistributionChart.destroy(); }
  const labels = loanData.map(d => d.product);
  const data = loanData.map(d => d.amount);
  loanDistributionChart = new Chart(document.getElementById('loanDistributionChart'), {
      type: 'pie',
      data: {
          labels: labels,
          datasets: [{
              data: data,
              backgroundColor: ['#0d6efd','#198754','#ffc107']
          }]
      }
  });
}

function updateStatusSummaryChart(data) {
  if (statusSummaryChart) { statusSummaryChart.destroy(); }
  statusSummaryChart = new Chart(document.getElementById('statusSummaryChart'), {
      type: 'bar',
      data: {
          labels: ['Approved', 'Pending'],
          datasets: [{
              label: 'Applications',
              data: [data.totalApprovedLoans, data.totalPendingLoans],
              backgroundColor: ['#198754', '#ffc107']
          }]
      },
      options: {
          plugins: { legend: { display: false } },
          scales: { y: { beginAtZero: true } }
      }
  });
}

function updateTopProductsList(loanDistribution) {
  const list = document.getElementById('topProductsList');
  list.innerHTML = ''; // Clear existing items

  loanDistribution.slice(0, 3).forEach(item => {
      const listItem = document.createElement('li');
      listItem.className = 'list-group-item';
      const formattedAmount = `₹${item.amount.toLocaleString(undefined, { minimumFractionDigits: 0, maximumFractionDigits: 0 })}`;
      listItem.textContent = `${item.product} – ${formattedAmount}`;
      list.appendChild(listItem);
  });
}

function updateMonthlyPerformanceTable(monthlyPerformance) {
  const tableBody = document.getElementById('monthlyPerformanceTable');
  tableBody.innerHTML = ''; // Clear existing rows

  monthlyPerformance.forEach(monthData => {
      const row = document.createElement('tr');
      row.innerHTML = `
          <td>${monthData.month}</td>
          <td>${monthData.applications}</td>
          <td>${monthData.approved}</td>
          <td><span class="badge bg-${monthData.approvalRate.includes('100') ? 'success' : 'warning'} text-dark">${monthData.approvalRate}</span></td>
          <td>₹${monthData.amount.toLocaleString(undefined, { minimumFractionDigits: 0, maximumFractionDigits: 0 })}</td>
      `;
      tableBody.appendChild(row);
  });
}

window.addEventListener('load', loadDashboardData);


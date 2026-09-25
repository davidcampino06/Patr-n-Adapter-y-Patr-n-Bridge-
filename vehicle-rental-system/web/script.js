// ---------------------------------------------------------
// DriveRent frontend logic.
// This file only handles the UI: it collects form data,
// calls the Java backend (/api/calculate), and renders the
// response. All the Adapter + Bridge pattern logic lives in
// the Java backend (see src/service/RentalService.java).
// ---------------------------------------------------------

const vehicleCards = document.querySelectorAll(".vehicle-card");
const vehicleTypeInput = document.getElementById("vehicle-type");

const planSelect = document.getElementById("plan");
const durationInput = document.getElementById("duration");
const durationLabel = document.getElementById("duration-label");
const customerInput = document.getElementById("customer");
const formError = document.getElementById("form-error");

const calculateBtn = document.getElementById("calculate-btn");
const confirmBtn = document.getElementById("confirm-btn");
const resultCard = document.getElementById("result-card");

const priceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  maximumFractionDigits: 0
});

const durationLabels = {
  daily: "Number of days",
  weekly: "Number of weeks",
  monthly: "Number of months"
};

const periodLabels = {
  daily: "Price per day",
  weekly: "Price per week",
  monthly: "Price per month"
};

// --- Vehicle selection ---
vehicleCards.forEach(card => {
  card.addEventListener("click", () => {
    vehicleCards.forEach(c => c.classList.remove("active"));
    card.classList.add("active");
    vehicleTypeInput.value = card.dataset.vehicle;
    resetResult();
  });
});

// --- Duration label changes with the selected plan ---
planSelect.addEventListener("change", () => {
  durationLabel.textContent = durationLabels[planSelect.value];
  resetResult();
});

// --- Calculate Rental ---
calculateBtn.addEventListener("click", () => runCalculation(false));

// --- Confirm Rental ---
confirmBtn.addEventListener("click", () => runCalculation(true));

function runCalculation(confirm) {
  const customerName = customerInput.value.trim();

  if (customerName === "") {
    formError.classList.remove("hidden");
    customerInput.focus();
    return;
  }
  formError.classList.add("hidden");

  const params = new URLSearchParams({
    vehicle: vehicleTypeInput.value,
    plan: planSelect.value,
    duration: durationInput.value || "1",
    customer: customerName,
    confirm: confirm ? "true" : "false"
  });

  fetch("/api/calculate?" + params.toString())
    .then(response => response.json())
    .then(data => {
      if (data.error) {
        alert("Error: " + data.error);
        return;
      }
      renderResult(data, confirm);
    })
    .catch(() => {
      alert("Could not reach the DriveRent server. Make sure it is running.");
    });
}

function renderResult(data, confirmed) {
  document.getElementById("res-customer").textContent = data.customerName;
  document.getElementById("res-vehicle").textContent = data.vehicleBrand + " " + data.vehicleName;
  document.getElementById("res-plan").textContent = data.planName;
  document.getElementById("res-duration").textContent =
    data.duration + " " + (data.planName === "Daily" ? "day(s)" : data.planName === "Weekly" ? "week(s)" : "month(s)");

  document.getElementById("res-period-label").textContent = periodLabels[planSelect.value];
  document.getElementById("res-period-price").textContent = priceFormatter.format(data.pricePerPeriod);
  document.getElementById("res-total").textContent = priceFormatter.format(data.totalPrice);

  const statusEl = document.getElementById("res-status");
  statusEl.textContent = "✓ " + data.status;

  resultCard.classList.remove("hidden");
  confirmBtn.disabled = false;

  if (confirmed) {
    confirmBtn.disabled = true;
    calculateBtn.disabled = true;
  }
}

function resetResult() {
  resultCard.classList.add("hidden");
  confirmBtn.disabled = true;
  calculateBtn.disabled = false;
}

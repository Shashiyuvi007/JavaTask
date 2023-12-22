// Sample station data (replace with actual data)
const stations = ["Station A", "Station B", "Station C", "Station D", /* ... */];

// Populate dropdowns with station options
const startStationDropdown = document.getElementById("startStation");
const endStationDropdown = document.getElementById("endStation");

stations.forEach(station => {
    const option = document.createElement("option");
    option.text = station;
    startStationDropdown.add(option);
    
    const optionCopy = document.createElement("option");
    optionCopy.text = station;
    endStationDropdown.add(optionCopy);
});

// Function to handle ticket booking
function bookTicket() {
    const startStation = startStationDropdown.value;
    const endStation = endStationDropdown.value;

    // Call backend API to book ticket and get response
    const ticketDetails = bookTicketBackend(startStation, endStation);

    // Display ticket details to the user
    const ticketDetailsContainer = document.getElementById("ticketDetails");
    ticketDetailsContainer.innerHTML = `<p>Ticket ID: ${ticketDetails.id}</p>
                                       <p>Price: ${ticketDetails.price}</p>`;
}

// Mock function to simulate backend API call
function bookTicketBackend(startStation, endStation) {
    // Replace with actual backend API call
    const ticketId = generateUniqueId();
    const price = calculateTicketPrice(startStation, endStation);

    return { id: ticketId, price: price };
}

// Mock function to generate a unique ticket ID
function generateUniqueId() {
    // Replace with actual unique ID generation logic
    return Math.random().toString(36).substring(7);
}

// Mock function to calculate ticket price
function calculateTicketPrice(startStation, endStation) {
    // Replace with actual price calculation logic based on station data
    const stationPrices = { "Station A": 0, "Station B": 5, "Station C": 15, "Station D": 50 /* ... */ };
    return Math.abs(stationPrices[endStation] - stationPrices[startStation]);
}

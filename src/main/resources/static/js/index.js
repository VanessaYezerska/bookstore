// Wait for the DOM to fully load before running the script
document.addEventListener("DOMContentLoaded", function() {

    // Select all images within the element that has the class 'slideshow'
    const slides = document.querySelectorAll(".slideshow img");
    // Initialize a variable to keep track of the current slide index
    let currentSlide = 0;

    // Function to switch to the next slide
    function nextSlide() {
        // Remove the 'visible' class from the current slide to hide it
        slides[currentSlide].classList.remove("visible");
        // Update the current slide index to the next slide
        // Use modulo operator to loop back to the first slide after the last one
        currentSlide = (currentSlide + 1) % slides.length;
        // Add the 'visible' class to the new current slide to show it
        slides[currentSlide].classList.add("visible");
    }

    // Set an interval to automatically switch to the next slide every 4 seconds (4000 milliseconds)
    setInterval(nextSlide, 4000);
});

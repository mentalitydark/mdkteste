const btnMenu = document.querySelector("#btnMenu");
const navbar = document.querySelector("#navbar");
const content = document.querySelector("#content");
const search = document.querySelector("#search");
const searchInput = document.querySelector("#searchInput");

btnMenu.addEventListener("click", () => {
    btnMenu.classList.toggle("Menu-active");
    navbar.classList.toggle("active");
    content.classList.toggle("adjust");
});

search.addEventListener("click", () => {
    window.location = "/imgs/"+searchInput.value;
});

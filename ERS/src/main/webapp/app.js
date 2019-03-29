window.onload = function() {
	console.log('app loading..');
	loadLandingView();
	//test("llevit", "lev123");
}

function loadLandingView() {
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4) {
			console.log('Servlet response received');
			if (xhr.status == 200) {
				console.log("Loading login.view");
				$('#view').html(xhr.responseText);
				if (xhr.responseText != "already_logged_in")
					$('#submit').on('click', loginUser);
			}
		}
	}
	xhr.open("GET", "login.view");
	xhr.send();
}

function loginUser() {

	let username = $('#username').val();
	let password = $('#password').val();

	if (validateStrings(username) || validateStrings(password)) {
		let xhr = new XMLHttpRequest();
		let toSend = [ username, password ];
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				let user = xhr.responseText;
				if (user == "invalid")
					$('#message').html('<font color="red">Sorry! Invalid credentials!</font>');
				else
					loadHomeView(user);
			}
		}
		xhr.open("POST", "login");
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(JSON.stringify(toSend));
	} else
		$('#message').html('Please enter valid username and password!');
}

function loadHomeView(user) {
	let userArray = user.split(" ");
	let firstName = userArray[0];
	let lastName = userArray[1];
	let role = userArray[2];
	console.log("Loging in: " + userArray);
	if (role.toLowerCase() == "fi_manager") {
		view = "manager.view";
		loadEventListeners = managerEventListeners;
	} else {
		view = "employee.view";
		loadEventListeners = employeeEventListeners;
	}
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.responseText) {
			$('#view').html(xhr.responseText);
			$('#name').html(firstName + " " + lastName);
			loadEventListeners();
		}
	}
	console.log("Loading " + view);
	xhr.open("GET", view);
	xhr.send();
}

function employeeEventListeners() {
	console.log("Employee event listeners started");
}

function managerEventListeners() {
	console.log("Manager event listeners started");
}

function getReinbursements() {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.responseText) {
			$('#view').html(xhr.responseText);
			// $('#goToLogin').on('click', loadLoginView);
			//			
			// //make sure that username is unique before allowing user to
			// submit
			// $('#username').on('blur', validateUser);
			//			
			// //create new user
			// $('#addUser').on('click', addUser);
		}
	}
	xhr.open("GET", "employee");
	xhr.send();
}

function validateStrings(str) {
	if (str == null || str == '')
		return false;
	else
		return true;
}

//This function is for testing only
function test(username, password) {
	if (validateStrings(username) || validateStrings(password)) {
		let xhr = new XMLHttpRequest();
		let toSend = [username, password];
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				let user = xhr.responseText;
				if (user == "invalid")
					$('#view').html('<font color="red">Sorry! Invalid credentials!</font>');
				else
					loadHomeView(user);
			}
		}
		xhr.open("POST", "login");
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(JSON.stringify(toSend));
	} else
		$('#view').html('Please enter valid username and password!');
}
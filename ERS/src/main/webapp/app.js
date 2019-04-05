let id = 0;

window.onload = function() {
	console.log('app loading..');
	loadLandingView();
	//test("gbonds", "gen123");
}

function loadLandingView() {
	$('#view').html("Loading Login Page...");
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			console.log("Loading login.view");
			$('#view').html(xhr.responseText);
			$('#submit').on('click', loginUser);
		}
		else if (xhr.readyState == 4 && xhr.status != 200)
			$('#view').html(xhr.status + " status code");
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
					$('#message').html('<font color="red">Invalid credentials!</font>');
				else
					loadHomeView(user);
			}
			else if (xhr.readyState == 4 && xhr.status != 200)
				$('#view').html(xhr.status + " status code");
				
		}
		xhr.open("POST", "login");
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(JSON.stringify(toSend));
	} else
		$('#message').html('Please enter valid username and password!');
}

function loadHomeView(user) {
	$('#view').html("Loading Main Page...");
	let userArray = user.split(" ");
	let firstName = userArray[0];
	let lastName = userArray[1];
	let role = userArray[2];
	
	console.log("Loging in: " + userArray);
	
	if (role == "Fi_Manager") {
		view = "manager.view";
		loadTable = loadManagerTable;
	}
	else {
		view = "employee.view";
		loadTable = loadEmployeeTable;	
	}
	
	let xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.responseText) {
			$('#view').html(xhr.responseText);
			$('#name').html(firstName + " " + lastName);
			loadTable();
			loadEventListeners();
		}
	}
	console.log("Loading " + view);
	xhr.open("GET", view);
	xhr.send();
}

function loadEventListeners() {
	$('#logout').on('click', loadLandingView);
	$('#add_button').on('click', loadAddExpenseForm);
	$('#approve').on('click', ()=>updateStatus(id, "Approved"));
	$('#deny').on('click', ()=>updateStatus(id, "Denied"));
	$('#pending').on('click', pendingFilter);
}

function loadEmployeeTable() {
	let xhr = new XMLHttpRequest;
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.responseText) {
			let reimbursements = JSON.parse(xhr.responseText);
			$('#employee_table').DataTable( {
				destroy: true,
				data: reimbursements,
				columns: [
					{data: 'submitted'},
					{data: 'type'},
					{data: 'amount', 
						render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
					{data: 'status'}
				]
			} );
		}
	}
	xhr.open("GET", "employee");
	xhr.send();	
}

function loadAddExpenseForm() {
	$('#add_button').attr("disabled", true);
	$('#add_form').load('partials/expense_form.html', function() {
	$('#cancel').on('click', ()=> {
			$('#add_form').html(""); 
			$('#add_button').attr("disabled", false);
		});
		$('#submit').on('click', addExpense);
	});	
}

function addExpense() {
	let type = $('#type').children("option:selected").val();
	let amount = $('#amount').val();
	let description = $('#description').val();
	
	if (validateStrings(type) && validateStrings(amount)) {
		let xhr = new XMLHttpRequest();
		let toSend = [type, amount, description];
		console.log(toSend);
		xhr.onreadystatechange = function() {
			if (xhr.readyState == 4 && xhr.status == 200) {
				let valid = xhr.responseText;
				if (valid == "false") {
					$('#message').html('<font color="red">Expense was not added.</font>');
				}
				else {
					$('#add_form').html("");
					$('#add_button').attr("disabled", false);
					loadEmployeeTable()
				}
			}
		}
		xhr.open("POST", "AddExpense");
		xhr.setRequestHeader("Content-type", "application/json");
		xhr.send(JSON.stringify(toSend));	
	}
	else {
		$('#message').html('<font color="red">Please complete the form.</font>');
	}		
}

function loadManagerTable() {
	$('#pending').html('Pending Only');
	let xhr = new XMLHttpRequest;
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.responseText) {
			let reimbursements = JSON.parse(xhr.responseText);
			let table = $('#manager_table').DataTable( {
				destroy: true,
				select: 'single',
				"pageLength": 50,
			    data: reimbursements,
				columns: [
					{data: 'submitted'},
					{data: 'author'},
					{data: 'type'},
					{data: 'amount', 
						render: $.fn.dataTable.render.number( ',', '.', 2, '$' )},
					{data: 'status'},
					{data: 'resolved'},
					{data: 'resolver'}
				]
			} );
			table.on( 'select', function ( e, dt, type, indexes ) {
			    if ( type === 'row' ) {
			        let data = table.rows(indexes).data().pluck('id');
			        id = data[0];
			        $('#approve').attr("disabled", false);
			        $('#deny').attr("disabled", false);
			        $('#description').html(reimbursements[id-1].description);
			    }
			} );
		}
	}
	xhr.open("GET", "manager");
	xhr.send();	
}

function updateStatus(id, status) {
    $('#approve').attr("disabled", true);
    $('#deny').attr("disabled", true);
	let xhr = new XMLHttpRequest();
	console.log("Approved or Deny Button Clicked on id:" + id);
	let toSend = [id, status];
	//console.log(toSend);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			let valid = xhr.responseText;
			if (valid == "false") {
				$('#message').html('<font color="red">Status was not updated.</font>');
			}
			else {
				loadManagerTable();
				$('#description').html("");
			}
		}
	}
	xhr.open("POST", "UpdateStatus");
	xhr.setRequestHeader("Content-type", "application/json");
	xhr.send(JSON.stringify(toSend));
}


function pendingFilter() {
	console.log('Pending button clicked');
	let table = $('#manager_table').DataTable();
	if ($('#pending').html() == 'Pending Only') {   
		table
	        .columns(4)
	        .search('Pending')
	        .draw();
	    $('#pending').html('Show All');
	}
	else {
		table
        	.columns(4)
        	.search('')
        	.draw();
		$('#pending').html('Pending Only');
	}
		
}

function validateStrings(str) {
	if (str == null || str == '')
		return false;
	else
		return true;
}

// This function is for testing only
function test(username, password) {
	if (validateStrings(username) || validateStrings(password)) {
		let xhr = new XMLHttpRequest();
		let toSend = [ username, password ];
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
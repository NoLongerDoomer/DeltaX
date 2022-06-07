<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<!DOCTYPE html>
<html>

<head>
	<meta charset="ISO-8859-1">
	<title>Add Tracks</title>
	<script type="text/javascript" src="static/js/jquery.min.js"></script>
	<script type="text/javascript" src="static/js/bootstrap.js"></script>
	<link rel="stylesheet" href="static/css/bootstrap.css">
	<link rel="stylesheet" href="static/css/multi-select.css">
	<script type="text/javascript" src="static/js/multi-select.js"></script>
</head>

<body>
	<div class="container">
		<nav class="navbar navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">
					DeltaX </a>
			</div>
		</nav>
		<div class="row mt-3 mb-3">
			<div class="col">Upload Image</div>
			<div class="col">
				<input type="file" name="cover" accept="image/*">
			</div>
		</div>
		<div class="row mb-4">
			<div class="col">
				<label>Song Name</label>
			</div>
			<div class="col">
				<input type="text" name="songname">
			</div>
		</div>
		<div class="row mb-4">
			<div class="col">
				<label>Date Released</label>
			</div>
			<div class="col">
				<input type="date" name="date_released">
			</div>
		</div>
		<div class="row mb-4">
			<div class="col">
				<label>Artist</label>
			</div>
			<div class="col">
				<select name="artists" id="artistslist">

				</select>
			</div>
		</div>
		<div class="row mb-4">
			<div class="col">
				<label>If you Don't see your artists in Multi-select Click to
					add</label>
			</div>
			<div class="col">
				<div>
					<button class="btn btn-primary" onclick="$('#addartistsmodal').modal('show');">Add Artist</button>
				</div>
			</div>
		</div>
		<div class="row">
			<div class="col">
				<button class="btn btn-primary" id="addtracks">Add</button>
			</div>
			<div class="col">
				<button class="btn btn-secondary">Back</button>
			</div>
		</div>
	</div>

	<div class="modal" tabindex="-1" id="addartistsmodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Add Artist</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row mb-4 mt-3">
							<div class="col">
								<label>Artist Name</label>
							</div>
							<div class="col">
								<input type="text" name="artistname">
							</div>
						</div>
						<div class="row mb-4">
							<div class="col">
								<label>Date of Birth</label>
							</div>
							<div class="col">
								<input type="date" name="dateofbirth">
							</div>
						</div>
						<div class="row mb-4">
							<div class="col">
								<label>Bio</label>
							</div>
							<div class="col">
								<input type="text" name="bio">
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="addartistbutton">Add</button>
				</div>
			</div>
		</div>
	</div>
	<script>
		$(document).ready(function () {
			$.ajax({
				url: "getartists",
				type: "GET",
				dataType: "json",
				success: function (result) {
					$.each(result, function (i, item) {
						$('#artistslist').append(
							"<option value = '" + item + "'>" + item + "</option>");
					})
					$('#artistslist').multiSelect();
					$('#artistslist').multiSelect('deselect_all');
				},
				error: function (error) {
					console.log(error.status);
				}
			})
		});

		$('#addtracks').click(function () {
			var selected = [];
			var selection = $('.ms-selected span');
			for (var elements of selection) {
				selected.push(elements.innerHTML);
			}
			var formData = new FormData();
			formData.append("image", $("[name = 'cover']")[0].files[0]);
			formData.append("artists", selected);
			formData.append("dateOfRelease", $("[name = 'date_released']").val());
			formData.append("name", $("[name = 'songname']").val());
			$.ajax({
				url: "addsong",
				type: "POST",
				processData: false,
				contentType: false,
				async: false,
				cache: false,
				data: formData,
				success: function (result) {
					alert(result);
					location.reload();
				},
				error: function (error) {
					console.log(error.status);
				}
			})
		});

		$('#addartistbutton').click(function () {
			$.ajax({
				url: "addartist",
				type: "POST",
				data: {
					"name": $("[name = 'artistname']").val(),
					"dateofbirth": $("[name = 'dateofbirth']").val(),
					"bio": $("[name = 'bio']").val()
				},
				success: function (result) {
					alert(result);
				},
				error: function (error) {
					console.log(error.status);
				}
			})
		});
	</script>

</body>

</html>
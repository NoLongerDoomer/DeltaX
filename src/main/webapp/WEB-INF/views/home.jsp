<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
	<meta charset="ISO-8859-1">
	<title>Spotify App</title>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/jquery.min.js"></script>
	<script type="text/javascript" src="${pageContext.request.contextPath}/static/js/bootstrap.js"></script>
	<link rel="stylesheet" href="${pageContext.request.contextPath}/static/css/bootstrap.css">
	<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">

	<style>
		#topsongstable img {
			height: 100px;
			width: auto;
			max-width: 100px;
			width: auto;
		}

		.checked {
			color: orange;
		}
	</style>
</head>

<body>
	<div class="container">
		<nav class="navbar navbar-light bg-light">
			<div class="container-fluid">
				<a class="navbar-brand" href="${pageContext.request.contextPath}/">
					DeltaX </a>
			</div>
		</nav>
	</div>
	<div class="container"></div>
	<div class="row justify-content-center">
		<div class="col-auto">
			<div class="row">
				<div class="col-10">Top 10 Songs</div>
				<div class="col-2">
					<button class="btn btn-sm btn-primary" onclick="location.href='addsongpage'">Add Songs</button>
				</div>
			</div>
			<table class="table table-responsive table-striped" id="topsongs">
				<thead>
					<tr>
						<th>Cover</th>
						<th>Title</th>
						<th>Date of Release</th>
						<th>Artists</th>
						<th>Average Ratings</th>
						<th>Rate</th>
					</tr>
				</thead>
				<tbody id="topsongstable">
				</tbody>
			</table>
			<hr>
			<div class="row">
				<div class="col">
					<label>Top 10 Artists</label>
				</div>
			</div>
			<table class="table table-striped">
				<thead>
					<tr>
						<th>Artists</th>
						<th>Date of birth</th>
						<th>Tracks</th>
					</tr>
				</thead>
				<tbody id="topartists">
				</tbody>
			</table>
		</div>
	</div>

	<div class="modal" tabindex="-1" id="ratesongsmodal">
		<div class="modal-dialog">
			<div class="modal-content">
				<div class="modal-header">
					<h5 class="modal-title">Rate This Track</h5>
					<button type="button" class="btn-close" data-bs-dismiss="modal" aria-label="Close"></button>
				</div>
				<div class="modal-body">
					<div class="container">
						<div class="row mb-4 mt-3">
							<div class="col">
								<label>Username</label>
							</div>
							<div class="col">
								<input type="text" name="username">
							</div>
						</div>
						<div class="row mb-4">
							<div class="col">
								<label>Email</label>
							</div>
							<div class="col">
								<input type="text" name="email">
							</div>
						</div>
						<div class="row mb-4">
							<div class="col">
								<label>Rate</label>
							</div>
							<div class="col">
								<span id="star1" class="fa fa-star "></span> <span id="star2"
									class="fa fa-star "></span> <span id="star3" class="fa fa-star "></span> <span
									id="star4" class="fa fa-star"></span>
								<span id="star5" class="fa fa-star"></span>
							</div>
						</div>
						<div class="row mb-4">
							<div class="col">
								<label>Review</label>
							</div>
							<div class="col">
								<textarea rows="4" cols="22.5" placeholder="write a review" name="review"></textarea>
							</div>
						</div>
					</div>
				</div>
				<div class="modal-footer">
					<button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close</button>
					<button type="button" class="btn btn-primary" id="ratetrack">Rate</button>
				</div>
			</div>
		</div>
	</div>

	<Script>
	
	/* Ajax to Fetch Top Tracks and top artists From The Controller */
		$(document)
			.ready(
				function () {
					$
						.ajax({
							url: "gettracks",
							type: "GET",
							dataType: "json",
							success: function (result) {
								
								/* Appending items from Ajax Response */
								$
									.each(
										result,
										function (i, item) {
											$(
													'#topsongstable')
												.append(
													"<tr> <td> " +
													"<img src = '${pageContext.request.contextPath}/static/images/" +
													item.imageUrl + "' alt = '" + item.imageUrl + "'/>" +
													"</td> <td>" +
													item.name +
													"</td> <td>" +
													item.dateOfRelease +
													"</td> <td> " +
													item.artists +
													"</td> <td> " +
													item.ratings +
													" </td> <td>" +
													"<button class = 'btn btn-dark'>Rate</button> </td> </tr>");
										})
							},
							error: function (error) {
								console.log(error.status);
							}
						})

					$.ajax({
						url: "gettopartist",
						type: "GET",
						dataType: "json",
						success: function (result) {
							console.log(result);
							$.each(result, function (i, item) {
								$('#topartists').append(
									"<tr> <td> " + item.name +
									"</td> <td> " +
									item.dateofbirth +
									"</td> <td> " +
									item.tracks +
									"</td> </tr>");
							})
						}
					})
				});

		$('#topsongs').on('click', "button", function () {
			var rating;
			$('#ratesongsmodal').modal('show');
			var rowArray = [];
			var $row = $(this).closest("tr"),
				$tds = $row.find("td");
			$.each($tds, function () {
				rowArray.push($(this).text());
			});
			console.log(rowArray);
			
			/* Getting Star Rating */
			
			$('#ratesongsmodal .fa').click(function () {
				$("#ratesongsmodal span").removeClass('checked');
				rating = ($(this)[0].id).substring(4);
				for (var i = 1; i <= rating; i++) {
					$("#star" + i).addClass("checked");
				}
			})

			$('#ratetrack').click(function () {
				$.ajax({
					type: "POST",
					url: "ratetrack",
					data: {
						"username": $('[name = "username"]').val(),
						"email": $('[name = "email"]').val(),
						"ratings": rating,
						"review": $('[name = "review"]').val(),
						"dateOfRelease": rowArray[2],
						"trackName": rowArray[1]
					},
					success: function (result) {
						alert(result);
						location.reload();
					}
				})
			})
		})
	</Script>
</body>

</html>
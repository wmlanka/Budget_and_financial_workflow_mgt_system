<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.0 Transitional//EN">
<html>
<head>
<script src="js/jquery-1.11.1.min.js"></script>
<script>
	$(function() {
		$('#state').html('');
		$.getJSON("readStates", function(res) {
			for (var i = 0; i < res.states.length; i++) {
				$('#state').append(
						'<option value=' + res.states[i] + '>' + res.states[i]
								+ '</option>');
			}
		});
		$("#state").change(
				function() {
					$('#district').html('');
					var state = {
						"state" : $("#state").val()
					};
					$.ajax({
						url : "readDistricts",
						data : JSON.stringify(state),
						dataType : 'json',
						contentType : 'application/json',
						type : 'POST',
						async : true,
						success : function(res) {
							console.log(res.districts.length);
							for (var i = 0; i < res.districts.length; i++) {
								console.log(" " + res.districts[i]);
								$('#district').append(
										'<option value=' + res.districts[i] + '>'
												+ res.districts[i]
												+ '</option>');
							}
						}
					});
				});
	});
</script>
</head>
<body>
	<h3>Struts 2 Dynamic Drop down List</h3>
	State :
	<select id="state"></select>District :
	<select id="district"></select>
</body>
</html>

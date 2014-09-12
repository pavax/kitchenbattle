<!DOCTYPE html>
<html>
<head lang="en">
    <meta charset="UTF-8">
    <title>Kitchenbattle Login</title>
    <link rel="stylesheet" href="/vendor/bootstrap/dist/css/bootstrap.css"/>

    <meta name="apple-mobile-web-app-title" content="Kitchenbattle Voting App">
    <meta name="apple-mobile-web-app-capable" content="yes">

    <link rel="apple-touch-icon" sizes="57x57" href="images/apple-touch-icon-57x57.png">
    <link rel="apple-touch-icon" sizes="114x114" href="images/apple-touch-icon-114x114.png">
    <link rel="apple-touch-icon" sizes="72x72" href="images/apple-touch-icon-72x72.png">
    <link rel="apple-touch-icon" sizes="60x60" href="images/apple-touch-icon-60x60.png">
    <link rel="apple-touch-icon" sizes="120x120" href="images/apple-touch-icon-120x120.png">
    <link rel="apple-touch-icon" sizes="76x76" href="images/apple-touch-icon-76x76.png">
    <link rel="icon" type="image/png" href="images/favicon-96x96.png" sizes="96x96">
    <link rel="icon" type="image/png" href="images/favicon-16x16.png" sizes="16x16">
    <link rel="icon" type="image/png" href="images/favicon-32x32.png" sizes="32x32">
    <meta name="msapplication-TileColor" content="#da532c">
    <style>
        body {
            background: url(http://lorempixel.com/1024/1024/food/) no-repeat center center fixed;
            -webkit-background-size: cover;
            -moz-background-size: cover;
            -o-background-size: cover;
            background-size: cover;
        }

        .panel-default {
            opacity: 0.9;
            margin-top: 30px;
        }

        .form-group.last {
            margin-bottom: 0px;
        }
    </style>
</head>
<body>

<div class="container">
    <div class="row">
        <div class="col-md-4 col-md-offset-7">
            <div class="panel panel-default">
                <div class="panel-heading">
                    <span class="glyphicon glyphicon-lock"></span> Login
                </div>
                <div class="panel-body">
                    <form class="form-horizontal" role="form" action="${rc.contextPath}/login" method="post">
                        <div class="form-group">
                            <label for="inputEmail3" class="col-sm-3 control-label">
                                Email</label>

                            <div class="col-sm-9">
                                <input name="username" type="text" class="form-control" id="username" placeholder="username" required>
                            </div>
                        </div>
                        <div class="form-group">
                            <label for="inputPassword3" class="col-sm-3 control-label">
                                Password</label>

                            <div class="col-sm-9">
                                <input name="password" type="password" class="form-control" id="password" placeholder="Password"
                                       required>
                            </div>
                        </div>

                        <div class="form-group last">
                            <div class="col-sm-offset-3 col-sm-9">
                                <button type="submit" class="btn btn-success btn-sm">
                                    Sign in
                                </button>
                                <button type="reset" class="btn btn-default btn-sm">
                                    Reset
                                </button>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>


</body>
</html>
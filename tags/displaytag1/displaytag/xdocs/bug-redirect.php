<?php

// handle links to sf and codehaus tracker

if (strncasecmp($item, "DISP", 4))
{
header("Location: http://sourceforge.net/support/tracker.php?aid=$item");
}
else
{
header("Location: http://jira.codehaus.org/browse/$item");
}

?>

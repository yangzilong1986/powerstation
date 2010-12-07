#*******************************************************************************
# Copyright (c) 2010 PSS Corporation and others.
# All rights reserved. This program and the accompanying materials
# are made available under the terms of the Eclipse Public License v1.0
# which accompanies this distribution, and is available at
# http://www.eclipse.org/legal/epl-v10.html
#
# Contributors:
#     PSS Corporation - initial API and implementation
#*******************************************************************************
#!/bin/sh
find ./ -type f | grep -v .svn | grep -v clean.sh | xargs rm -f

/**
 * File Upload Tester
 */

// Default File POST Endpoint
let uploadEndpoint = window.location.origin + '/api/uploadFile';

// Get Form Element References
let endpointSelector = document.querySelector('#endpoint-selector');
let uploadForm = document.querySelector('#upload-form');
let uploadAreaOverlay = document.querySelector('#upload-area-overlay');
let uploadArea = document.querySelector('#upload-area');
let statusBox = document.querySelector('#status-area');

/**
 * Logs a message to both the status area and
 * the console.
 * 
 * If arguments are not provided, a newline alone
 * is appended to the status area.
 * 
 * @param {String} category The message category.
 * @param {String} message The message content.
 */
function statusLog(category, message)
{
    // Log Message
    if (category && message)
    {
        let text = `[${category}]: ${message}`;
        console.log(text);
        statusBox.append(text);
    }
    // Append Newline To Status Box
    let newline = document.createElement('br');
    statusBox.appendChild(newline);
}

uploadForm.addEventListener('submit', uploadSubmit);
function uploadSubmit(event)
{
    event.preventDefault();
}

endpointSelector.addEventListener('change', updateEndpoint);
function updateEndpoint(event)
{
    uploadEndpoint = endpointSelector.value;
    statusLog('INFO', `Endpoint Updated: ${uploadEndpoint}`);
}

// Triggered If User Enters Upload Area While Dragging Content
uploadAreaOverlay.addEventListener('dragover', uploadDragOver);
function uploadDragOver(event)
{
    event.preventDefault();
    uploadAreaOverlay.classList.add('drag-over');
}

// Triggered If User Exits Upload Area While Dragging Content
uploadAreaOverlay.addEventListener('dragleave', uploadDragLeave);
function uploadDragLeave()
{
    uploadAreaOverlay.classList.remove('drag-over');
}

// Triggered If User Drops Content Into The Upload Area
uploadAreaOverlay.addEventListener('drop', uploadDrop);
function uploadDrop(event)
{
    event.preventDefault();
    uploadAreaOverlay.classList.remove('drag-over');
    statusLog('INFO', 'Received content, checking for files!');
    let content = event.dataTransfer.items;
    let numFiles = 0;
    for (let i = 0; i < content.length; i++)
    {
        if (content[i].kind === 'file')
        {
            postFile(content[i].getAsFile());
            numFiles++;
        }
    }
    // No Valid Files Were Provided
    if (numFiles === 0)
    {
        statusLog('WARN', 'No files were found!');
        uploadAreaOverlay.classList.add('invalid-content');
        setTimeout(
            function()
            {
                uploadAreaOverlay.classList.remove('invalid-content');
            }, 2000
        );
    }
}

// Triggered If Files Manually Selected
uploadAreaOverlay.addEventListener('change', uploadSelect);
function uploadSelect(event)
{
    statusLog('INFO', 'Files selected!');
    for (let i = 0; i < uploadArea.files.length; i++)
    {
        postFile(uploadArea.files[i]);
    }
}

/**
 * POSTs a file to the selected endpoint.
 * 
 * @param {File} file The file to POST.
 */
function postFile(file)
{
    statusLog('INFO', `Attempting to send ${file.name}`);
    let request = new XMLHttpRequest();
    let data = new FormData();
    data.append('file', file);
    request.open('POST', uploadEndpoint);
    request.send(data);
    statusLog('INFO', `Sent ${file.name}`);
}

// Final Initialization
endpointSelector.defaultValue = uploadEndpoint;
statusLog('INFO', 'File Upload Tester Initialized');
function goToMain() {
    const form = document.createElement('form');
    form.action = '/main';
    form.method = 'POST';
    document.body.appendChild(form);
    form.submit();
}
function goToHelp() {
    const form = document.createElement('form');
    form.action = '/help';
    form.method = 'POST';
    document.body.appendChild(form);
    form.submit();
}